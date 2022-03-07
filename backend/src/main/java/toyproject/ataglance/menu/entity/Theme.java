package toyproject.ataglance.menu.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;

@Table("ATAGLANCE_MENU_THEME")
@Getter @Setter
public class Theme implements Persistable<String>{

	@Id
	@Column("theme_id")
	private String id;
	@Column("theme_name")
	private String name;
	
	@CreatedDate
	private LocalDateTime dateCreated;
	@LastModifiedDate
	private LocalDateTime dateUpdated;

	private boolean enabled;
	
	@MappedCollection(keyColumn = "fk_theme_id", idColumn = "detail_id")
	List<Detail> details = new ArrayList<>();
	
	@Override
	public String getId() {
		return id;
	}
	@Override
	public boolean isNew() {
		return dateCreated == null;
	}
	
}
