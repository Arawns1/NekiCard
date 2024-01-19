package br.com.itneki.nekicard.user_photo.controller;

import br.com.itneki.nekicard.user_photo.domain.UserPhoto;
import br.com.itneki.nekicard.user_photo.dto.UserPhotoDTO;
import br.com.itneki.nekicard.user_photo.services.UserPhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;
@RequestMapping("/image")
@RestController
@RequiredArgsConstructor
@Tag(name="Foto do Usuário", description = "Endpoints da foto do usuário")
public class UserPhotoController {

    private final UserPhotoService userPhotoService;
    @GetMapping("/{id}")
    @Operation(summary = "Retornar Imagem", description = "Endpoint responsável por retornar imagem do Usuário com base no id da foto")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        UserPhoto userPhoto = userPhotoService.findById(id);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-type", "image/jpeg");
            headers.set("Content-Disposition", "inline; filename=\" " + userPhoto.getUser().getName() + " image");
            headers.set("Content-length", String.valueOf(userPhoto.getPhoto().length));
            return new ResponseEntity<>(userPhoto.getPhoto(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/{userId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Salvar imagem do usuário", description = "Endpoint responsável por salvar imagem do Usuário")
    @Transactional
    public ResponseEntity<Object> saveImage(@PathVariable UUID userId, @RequestPart("image") MultipartFile profilePhoto, UriComponentsBuilder uriBuilder) {
        try {
            var result = userPhotoService.save(userId, profilePhoto);
            var uri = uriBuilder.path("/image/{id}")
                                .buildAndExpand(result.getId())
                                .toUri();
            return ResponseEntity.created(uri).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
