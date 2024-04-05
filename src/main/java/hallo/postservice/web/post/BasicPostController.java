package hallo.postservice.web.post;

import hallo.postservice.domain.post.Post;
import hallo.postservice.domain.post.PostRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/posts")
@RequiredArgsConstructor
public class BasicPostController {

    private final PostRepository postRepository;

    @GetMapping
    public String posts(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "basic/posts";
    }

    @GetMapping("/{postId}")
    public String post(@PathVariable("postId") Long postId, Model model) {
        Post post = postRepository.findById(postId);
        model.addAttribute("post", post);
        return "basic/post";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addPostV1(@RequestParam("title") String title,
                            @RequestParam("writer") String writer,
                            @RequestParam("content") String content,
                            Model model) {
        Post post = new Post();
        post.setTitle(title);
        post.setWriter(writer);
        post.setContent(content);

        postRepository.save(post);

        model.addAttribute("post", post);

        return "basic/post";
    }

//    @PostMapping("/add")
    public String addPostV2(@ModelAttribute("post") Post post, Model model) {

        postRepository.save(post);

//        model.addAttribute("post", post);

        return "basic/post";
    }

//    @PostMapping("/add")
    public String addPostV3(@ModelAttribute Post post) {
        postRepository.save(post);
        return "basic/post";
    }

//    @PostMapping("/add")
    public String addPostV4(Post post) {
        postRepository.save(post);
        return "basic/post";
    }// V4보다는 V3 추천 - V4는 명시성이 너무 떨어짐

//    @PostMapping("/add")
    public String addPostV5(@ModelAttribute Post post) {
        postRepository.save(post);
        return "redirect:/basic/posts/" + post.getId();
    }

    @PostMapping("/add")
    public String addPostV6(@ModelAttribute Post post, RedirectAttributes redirectAttributes) {
        Post savedPost = postRepository.save(post);
        redirectAttributes.addAttribute("postId", savedPost.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/posts/{postId}";
    }

    @GetMapping("/{postId}/edit")
    public String editForm(@PathVariable("postId") Long postId, Model model) {
        Post post = postRepository.findById(postId);
        model.addAttribute("post", post);
        return "basic/editForm";
    }

    @PostMapping("/{postId}/edit")
    public String edit(@PathVariable("postId") Long postId, @ModelAttribute Post post) {
        postRepository.update(postId, post);
        return "redirect:/basic/posts/{postId}";
    }

    @PostMapping("{postId}/delete")
    public String delete(@PathVariable("postId") Long postId) {
        postRepository.delete(postId);
        return "redirect:/basic/posts";
    }
    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        postRepository.save(new Post("제목1", "침착맨", "너 자신을 알라"));
        postRepository.save(new Post("제목2", "주호민", "빢빢이"));
    }
}
