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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Table("ATAGLANCE_MENU_THEME")
@Getter
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
	
	@JsonIgnore
	@Override
	public String getId() {
		return id;
	}
	@JsonIgnore
	@Override
	public boolean isNew() {
		return dateCreated == null;
	}
	
	protected Theme() {}
	
	public Theme(String id, String name, boolean enabled) {
		this.id = id;
		this.name = name;
		this.enabled = enabled;
	} 
	
}
