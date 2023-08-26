package ru.taratonov.dossier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.taratonov.dossier.enums.Theme;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessageDTO {
    private String emailAddress;
    private Theme theme;
    private Long applicationId;
}
