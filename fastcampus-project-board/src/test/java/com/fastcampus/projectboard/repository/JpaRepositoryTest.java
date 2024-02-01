package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.config.JpaConfig;
import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class) // 테스트가 제대로 읽지 못하는 config가 있는데 JpaConfig이다. JpaConfig의 존재를 DataJpa테스트는 모르기 떄문에 import를 추가해주었다.
@DataJpaTest
class JpaRepositoryTest {

    //test할 내용 넣어준다.
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    public JpaRepositoryTest( //constructor를 만들어준다.
            @Autowired ArticleRepository articleRepository, //각 생성자 파라미터들에 @Autowired를 만들어준다.
            @Autowired ArticleCommentRepository articleCommentRepository,
                              @Autowired UserAccountRepository userAccountRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    } //여기까지 생성자 주입 패턴 완료

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() { //단순한 crud를 만들것이다.
        // Given


        // When
        List<Article> articles = articleRepository.findAll();

        //Then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() { //단순한 crud를 만들것이다.
        // Given
        long previousCount = articleRepository.count();
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("uno", "pw", null, null, null));
        Article article = Article.of(userAccount, "new article", "new content", "#spring");

        // When
        articleRepository.save(article);

        //Then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() { //단순한 crud를 만들것이다.
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        // When
        Article savedArticle = articleRepository.save(article);

        //Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }



    @DisplayName("Delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() { //단순한 crud를 만들것이다.
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        // When
        articleRepository.delete(article);

        //Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount -1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);



    }
}