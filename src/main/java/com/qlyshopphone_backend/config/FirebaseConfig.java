package com.qlyshopphone_backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @Bean
    public FirebaseApp firebaseInitialization() throws IOException{
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase/cuccushop-a2725-firebase-adminsdk-ppxte-91c9385591.json");
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("cuccushop-a2725.appspot.com")
                .build();
        return FirebaseApp.initializeApp(options);
    }
}
