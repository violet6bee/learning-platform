package shvalieva.learning_platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "biography")
    private String biography;

    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "avatarUrl")
    private String avatarUrl;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    public ProfileEntity(String biography, String phone, String avatarUrl) {
        this.biography = biography;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
    }
}
