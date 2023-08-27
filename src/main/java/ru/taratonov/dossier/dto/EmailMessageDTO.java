package ru.taratonov.dossier.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.taratonov.dossier.enums.Theme;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessageDTO {
    @Schema(
            description = "client's email",
            name = "emailAddress",
            example = "taratonovv8@bk.ru")
    private String emailAddress;

    @Schema(
            description = "Theme of email message",
            name = "theme",
            example = "CREATE_DOCUMENTS")
    private Theme theme;

    @Schema(
            description = "application id",
            name = "applicationId",
            example = "1")
    private Long applicationId;
}
