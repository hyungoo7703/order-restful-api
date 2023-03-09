package toyproject.ataglance.menu.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Table("ATAGLANCE_MENU_DETAIL")
@Getter
public class Detail implements Persistable<String>{
	
	@Id
	@Column("DETAIL_ID")
	private String id;
	@Column("DETAIL_NAME")
	private String name;
	
	private int price;
	private int margin;
	private String memo;
	
	@CreatedDate
	private LocalDateTime dateCreated;
	@LastModifiedDate
	private LocalDateTime dateUpdated;

	private boolean enabled;
	private boolean onEvent;

	@Column("FK_THEME_ID")
	private String themeId;

	@MappedCollection(idColumn = "DETAIL_ID")
	private Set<OrderDetail> orderDetails = new HashSet<>();

	@JsonIgnore
	@Override
	public boolean isNew() {
		return dateCreated == null;
	}

	protected Detail() {}
	
	public Detail(String id, String name, int price, int margin, String memo, boolean enabled, boolean onEvent, String themeId) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.margin = margin;
		this.memo = memo;
		this.enabled = enabled;
		this.onEvent = onEvent;
		this.themeId = themeId;
	}
	
}
