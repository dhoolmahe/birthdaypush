package com.render.birthdayreminder.notification;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BirthdayRequest {

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Date of birth cannot be null")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date of birth must be in the format YYYY-MM-DD")
    private String dob;
}
