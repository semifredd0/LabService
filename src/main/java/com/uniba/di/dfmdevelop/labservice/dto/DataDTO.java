package com.uniba.di.dfmdevelop.labservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class DataDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCercata;
}
