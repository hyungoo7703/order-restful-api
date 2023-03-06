package toyproject.ataglance.menu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table("ATAGLANCE_MENU_CATEGORY")
@Getter
public class Category implements Persistable<String> {

    @Id
    @Column("CATEGORY_ID")
    private String id;
    @Column("CATEGORY_NAME")
    private String name;

    @CreatedDate
    private LocalDateTime dateCreated;
    @LastModifiedDate
    private LocalDateTime dateUpdated;

    private boolean enabled;

    @MappedCollection(idColumn = "FK_CATEGORY_ID", keyColumn = "THEME_ID")
    private List<Theme> Themes = new ArrayList<>();

    @JsonIgnore
    @Override
    public boolean isNew() {
        return dateCreated == null;
    }

    protected Category() {}

    public Category(String id, String name, boolean enabled) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
    }
}
