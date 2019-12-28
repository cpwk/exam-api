package com.yang.exam.api.question.repository;

import com.yang.exam.api.question.model.Question;
import com.yang.exam.commons.reposiotry.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends BaseRepository<Question, Integer> {


    @Query(value = "select id from question where category_id=:categoryId and type=:type and difficulty=:difficulty and status=:status order by rand() limit :limit", nativeQuery = true)
    List<Integer> randomQuestionList(Integer categoryId, Byte type, Byte difficulty, Byte status, Integer limit);

//    @Transactional
//    @Query(value = "select * from question a inner join (select id from question where categoryId =: categoryId  and type in (type) type  ORDER BY RAND() limit num) b on a.id=b.id;", nativeQuery = true)
//    @Modifying
//    List findByCategoryIdAndType(Integer categoryId, List<Integer> type, Integer num);


//    参考
//    @Query(value = "select id from exam_question where status=:status and material_id=0 and exam_question_type_id in (:typeIds) and id not in(:existsIds) order by rand() limit :limit ", nativeQuery = true)
//    List<Integer> randomQuestionListByProps(int status, List<Integer> typeIds, List<Integer> existsIds, int limit);

//    整条返回
//    @Query(value = "SELECT * FROM `question` AS a join (select id from `question` where `category_id` =: category_id  and `type` =: type  ORDER BY RAND() limit :limit ) AS b on a.id=b.id", nativeQuery = true)
//    List<Integer> findByCategoryIdAndStatusAndType(int categoryId, int type, int status, int limit);


//    select * from `question` where category_id = category_id and type = type order by rand() LIMIT 5
//select id from `question` where category_id =category_id and status = status and type = type order by rand() limit :limit


}
