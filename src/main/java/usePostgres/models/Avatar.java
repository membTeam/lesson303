package usePostgres.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avatar {
    @Id
    private Long id;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private int fileSize;

    @Column(name = "media_type")
    private String mediaType;

    private byte[] data;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Student student;
}