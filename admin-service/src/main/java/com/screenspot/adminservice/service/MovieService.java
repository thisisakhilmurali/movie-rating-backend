package com.screenspot.adminservice.service;


import com.screenspot.adminservice.exception.MovieNotFoundException;
import com.screenspot.adminservice.model.Movie;
import com.screenspot.adminservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;


    @Value("${s3.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.folder}")
    private String folderName;

    private String region = "eu-north-1";



    public void addMovie(String name,String director,String genre,String releasedate,String language,String duration, String country,String description,double overallrate, MultipartFile imageFile) {
        // Upload image file to AWS S3
        String imageFileName = generateUniqueFileName(imageFile.getOriginalFilename());
        uploadImageToS3(imageFile, imageFileName);

        // Create Movie object and save it in the database
        Movie movie = new Movie(name,director,genre,releasedate,language, duration,country,description,overallrate, generateS3ImageUrl(imageFileName));
        movieRepository.save(movie);
    }

    public void updateMovie(Long id, String name,String director,String genre,String releasedate,String language,String duration, String country,String description,double overallrate, MultipartFile imageFile) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Movie not found with ID: " + id));

        // Delete existing image from AWS S3
        deleteImageFromS3(movie.getImageUrl());

        // Upload new image file to AWS S3
        String imageFileName = generateUniqueFileName(imageFile.getOriginalFilename());
        uploadImageToS3(imageFile, imageFileName);

        // Update movie details
        movie.setMovieName(name);
        movie.setDuration(duration);
        movie.setMovieGenre(genre);
        movie.setMovieReleaseDate(releasedate);
        movie.setMovieLanguage(language);
        movie.setDuration(duration);
        movie.setCountry(country);
        movie.setDescription(description);
        movie.setOverallRate(overallrate);
        movie.setImageUrl(generateS3ImageUrl(imageFileName));

        movieRepository.save(movie);
    }

    public List<Movie> view() {
        return movieRepository.findAll();
    }

    public Movie fetchById(long movieId) throws MovieNotFoundException {
        Optional<Movie> op= movieRepository.findById(movieId);
        if(op.isPresent()) {
            return op.get();
        }
        throw new MovieNotFoundException("movie not found with id : "+ movieId);

    }

    public void deleteMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found with ID: " + id));

        // Delete image from AWS S3
        deleteImageFromS3(movie.getImageUrl());

        // Delete the movie from the database
        movieRepository.delete(movie);
    }


    private void deleteImageFromS3(String imageUrl) {
        try {
            String imageFileName = extractImageFileName(imageUrl);

            S3Client s3Client = S3Client.builder()
                    .region(Region.EU_NORTH_1)
                    .credentialsProvider(() -> AwsBasicCredentials.create("A-KEY", "S-KEY"))
                    .build();

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(folderName + "/" + imageFileName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            System.out.println("Exception occurred during S3 image deletion: " + e.getMessage());
        }
    }


    private String extractImageFileName(String imageUrl) {
        String[] parts = imageUrl.split("/");
        String lastPart = parts[parts.length - 1];
        return lastPart;
    }

//    https://screenspot-posters.s3.eu-north-1.amazonaws.com/poster/7bdd7f60-19f4-43bb-98fd-ac7e401d856d.jpg
//        return "https://s3.amazonaws.com/" + bucketName + "/" + folderName + "/" + imageFileName;


    private String generateS3ImageUrl(String imageFileName) {
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com" + "/" + folderName + "/" + imageFileName;
    }

    private String generateUniqueFileName(String originalFilename) {
        // Generate a unique filename for the image using a UUID
        String uniqueFilename = UUID.randomUUID().toString();

        // Extract the file extension from the original filename, if present
        String fileExtension = "";
        int extensionIndex = originalFilename.lastIndexOf('.');
        if (extensionIndex >= 0 && extensionIndex < originalFilename.length() - 1) {
            fileExtension = originalFilename.substring(extensionIndex + 1);
        }

        // Append the file extension to the unique filename, if available
        if (!fileExtension.isEmpty()) {
            uniqueFilename += "." + fileExtension;
        }

        return uniqueFilename;
    }

    private void uploadImageToS3(MultipartFile imageFile, String imageFileName) {
        try {
            S3Client s3Client = S3Client.builder()
                    .region(Region.EU_NORTH_1)
                    .credentialsProvider(() -> AwsBasicCredentials.create("A-KEY", "S-KEY"))
                    .build();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(folderName + "/" + imageFileName)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(imageFile.getBytes()));
        } catch (Exception e) {
            System.out.println("Exception occurred during S3 upload: " + e.getMessage());
        }
    }

}
