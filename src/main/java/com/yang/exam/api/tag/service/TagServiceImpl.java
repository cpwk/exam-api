package com.yang.exam.api.tag.service;

import com.yang.exam.api.tag.model.Tag;
import com.yang.exam.api.tag.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-22 20:00
 * @Version：1.0
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public void save(Tag tag) throws Exception {
        if (tag.getId() == null) {
            tagRepository.save(tag);
        }
    }

    @Override
    public List<Tag> tag() throws Exception {
        return tagRepository.findAll();
    }

    @Override
    public void delete(Integer id) throws Exception {
        Tag tag = tagRepository.findById(id).get();
        if (tag.getId() != null) {
            tagRepository.delete(tag);
        }
    }

}