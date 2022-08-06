package com.hashedin.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import static javax.persistence.TemporalType.TIMESTAMP;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = "createdAt", allowGetters = true)
public abstract class Auditable implements Serializable {


    private static final long serialVersionUID = -119613145670627672L;

    /**
     * For auditing : CreatedDate TimeStamp.
     */
    @CreatedDate
    @Temporal(TIMESTAMP)
    @Column(name="createdAt", updatable=false)
    private Date createdAt;

}

