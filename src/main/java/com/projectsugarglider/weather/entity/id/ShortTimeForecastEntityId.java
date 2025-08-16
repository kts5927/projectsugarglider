package com.projectsugarglider.weather.entity.id;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ShortTimeForecastEntityId  implements Serializable{
    private Long id;

    private String year;

    private String month;

    private String day;
}
