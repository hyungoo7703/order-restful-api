package toyproject.ataglance.menu.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;

@Table("ATAGLANCE_MENU_DETAIL")
@Getter @Setter
public class Detail implements Persistable<String>{
	
	@Id
	@Column("detail_id")
	private String id;
	@Column("detail_name")
	private String name;
	private int price;
	private String memo;
	
	@CreatedDate
	private LocalDateTime dateCreated;
	@LastModifiedDate
	private LocalDateTime dateUpdated;

	private boolean enabled;
	@Column("fk_theme_id")
	private String themeId;
	
	@Override
	public String getId() {
		return id;
	}
	@Override
	public boolean isNew() {
		return dateCreated == null;
	}
	
}
