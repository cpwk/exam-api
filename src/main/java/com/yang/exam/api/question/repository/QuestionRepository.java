package com.yang.exam.api.question.repository;

import com.yang.exam.api.question.model.Question;
import com.yang.exam.commons.reposiotry.BaseRepository;

import java.util.List;

public interface QuestionRepository extends BaseRepository<Question, Integer> {

    List<Question> findAllByType(Byte type);

    List<Question> findAllByCategoryId(Integer categoryId);

    List<Question> findAllByDifficulty(Byte difficulty);

    List<Question> findByCategoryId(Integer categoryId);
}
