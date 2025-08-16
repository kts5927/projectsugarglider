package com.projectsugarglider.kepco.entity.id;

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
public class KepcoUsageEntityId implements  Serializable {
 
    private String year;

    private String month;

    private String lowerCode;

    private String upperCode;
}
