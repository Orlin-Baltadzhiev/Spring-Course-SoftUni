package courese.springdata.jasondemo.service.impl;

import courese.springdata.jasondemo.dao.PostRepository;
import courese.springdata.jasondemo.entity.Post;
import courese.springdata.jasondemo.exception.NonExisistingEnitityException;
import courese.springdata.jasondemo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class PostServicImpl implements PostService {
    private PostRepository postRepository;

    @Autowired
    public PostServicImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return this.postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(()->
                new NonExisistingEnitityException(String.format("Post with ID = %s does not exist.",id)));
    }

    @Transactional
    @Override
    public Post addPost(Post post) {
        post.setId(null);
        return postRepository.save(post);
    }

    @Transactional
    @Override
    public Post updatePost(Post post) {
        getPostById(post.getId());
        return postRepository.save(post);
    }

    @Transactional
    @Override
    public Post deletePost(Long id) {
        Post removed =  getPostById(id);
        postRepository.deleteById(id);
        return  removed;
    }

    @Override
    public long getPostsCount() {
        return postRepository.count();
    }
}
