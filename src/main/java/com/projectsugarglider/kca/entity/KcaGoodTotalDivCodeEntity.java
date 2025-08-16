package com.projectsugarglider.kca.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kca_good_total_div")
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Builder
public class KcaGoodTotalDivCodeEntity {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "code", nullable = false, length = 9)
    private String code;

    @Column(name = "code_name", nullable = false)
    private String codeName;

    @Column(name = "high_code", length = 9)
    private String highCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "high_code", referencedColumnName = "code",
                insertable = false, updatable = false)
    private KcaGoodTotalDivCodeEntity parent;

    @OneToMany(mappedBy = "parent")
    @Builder.Default
    private final List<KcaGoodTotalDivCodeEntity> children = new ArrayList<>();
}
