package hallo.postservice.domain.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostRepositoryTest {

    private PostRepository postRepository = new PostRepository();

    @AfterEach
    void afterEach() {
        postRepository.clearStore();
    }

    @Test
    void save() {

        //given
        Post post = new Post("postA", "minsu", "hi");
        Post savedPost = postRepository.save(post);

        //when
        Post findPost = postRepository.findById(post.getId());

        //then
        assertThat(savedPost).isEqualTo(findPost);
    }

    @Test
    void findAll() {
        //given
        Post post1 = new Post("postA", "minsu", "hi");
        Post post2 = new Post("postB", "jaehoon", "hello");
        postRepository.save(post1);
        postRepository.save(post2);

        //when
        List<Post> result = postRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(post1, post2);
    }

    @Test
    void updatePost() {
        //given
        Post post = new Post("postA", "minsu", "hi");
        Post savedPost = postRepository.save(post);
        Long postId = post.getId();
        //when
        Post updateParam = new Post("post", "youngsu", "hi2");
        postRepository.update(postId, updateParam);

        Post findPost = postRepository.findById(postId);
        //then
        assertThat(findPost.getWriter()).isEqualTo(updateParam.getWriter());
        assertThat(findPost.getTitle()).isEqualTo(updateParam.getTitle());
        assertThat(findPost.getContent()).isEqualTo(updateParam.getContent());
        assertThat(findPost.getDate()).isEqualTo(updateParam.getDate());
    }

    @Test
    void deletePost() {
        //given
        Post post1 = new Post("post1", "bella1", "morning1");
        Post post2 = new Post("post2", "bella2", "morning2");
        postRepository.save(post1);
        postRepository.save(post2);

        //when
        postRepository.delete(post1.getId());
        postRepository.delete(post2.getId());

        List<Post> result = postRepository.findAll();
        //then
        assertThat(result.size()).isEqualTo(0);
        assertThat(result).isEmpty();
    }
}