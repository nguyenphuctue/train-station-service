package com.jungdo.trainstationservice.entity;

import com.jungdo.trainstationservice.entity.enums.EntityType;
import com.jungdo.trainstationservice.entity.enums.FileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_files")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Files extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;

    private Long relationId;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Enumerated(EnumType.STRING)
    private EntityType entityType;

}
