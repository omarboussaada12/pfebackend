package web.tn.drobee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(	name = "Notification") 
public class Notification {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

		private String text;

		@Column(name = "recipient")
	    private String to;

	    public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
	    
		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}

		
		

}
