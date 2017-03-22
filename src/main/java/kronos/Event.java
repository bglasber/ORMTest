package kronos;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="EVENTS")
public class Event {

        @Id
        @GeneratedValue(generator="increment")
        @GenericGenerator(name="increment", strategy="increment")
        private Long id;

        @Basic
        @Column( name="TITLE" )
        private String title;

        public Event() {
        }

        public Event( String title ) {
            this.title = title;
        }

        public Long getId() {
            return id;
        }

        public void setId( Long id ) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle( String title ) {
            this.title = title;
        }
}
