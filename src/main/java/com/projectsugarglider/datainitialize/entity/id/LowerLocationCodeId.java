package com.projectsugarglider.datainitialize.entity.id;

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
public class LowerLocationCodeId implements Serializable {

    // 하위 지역코드
    private String lowerCode;

    // 상위 지역코드
    private String upperCode;

}
