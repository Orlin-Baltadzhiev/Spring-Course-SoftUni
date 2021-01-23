package courese.springdata.jasondemo.entity;

import com.google.gson.annotations.Expose;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Expose
    @NonNull
    @NotNull
    @Length(min = 3, max=80, message = "Title must be minimum 3 and maximum 80 charachters long.")
    private String title;

    @Expose
    @NonNull
    @NotNull
    @Length(min = 3, max=2048)
    private String content;

    @Expose
    @NonNull
    @NotNull
    @URL
    private String imageUrl;

    @NonNull
    @NotNull
    @Length(min = 3, max=80, message = "Author is reqired property (between 3 and 80 charachters long.")
    private String author;

    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

}
