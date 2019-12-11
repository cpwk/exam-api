package com.yang.exam.api;

import com.yang.exam.commons.entity.Constants;
import com.yang.exam.commons.exception.DetailedException;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/11 14:58
 * @Version：1.0
 */
public class Test {

//    @Override
//    public void save(Sort sort) throws ServiceException {
//
//        validateProductCategory(sort);
//
//        Integer id = sort.getId();
//        if (id == null || id == 0) {
//            sortRepository.save(sort);
//            clearExamQuestionTypes();
//        } else {
//            Sort of = sort(id);
//            of.setSequence(sort.getSequence());
//            of.setName(sort.getName());
//            of.setParentId(sort.getParentId());
//            of.setPriority(sort.getPriority());
//            sortRepository.save(of);
//            clearExamQuestionTypes();
//        }
//    }
//
//    private void validateProductCategory(Sort sort) throws ServiceException {
//
//        int parentId = sort.getParentId();
//        if (StringUtils.isEmpty(sort.getName())) {
//            throw new DetailedException("未知错误");
//        }
//        String sequence = sort.getSequence();
//        if (parentId > 0) {
//            Sort parent = sort(parentId);
//            int _parentId = parent.getParentId();
//            if (_parentId > 0) {
//                Sort grandPa = sort(_parentId);
//                System.out.println(parent.getSequence());
//                System.out.println(grandPa.getSequence());
//                if (!(sequence.substring(0, 2).equals(grandPa.getSequence().substring(0, 2))) && (sequence.substring(0, 4).equals(parent.getSequence().substring(0, 4)))) {
//                    throw new DetailedException("未知错误");
//                }
//            } else {
//                if (!sequence.substring(0, 2).equals(parent.getSequence().substring(0, 2))) {
//                    throw new DetailedException("未知错误");
//                }
//            }
//        }
//        if (sort.getStatus() == 0) {
//            sort.setStatus(Constants.STATUS_HALT);
//        }
//        if (sort.getPriority() == 0) {
//            sort.setPriority(1);
//        }
//    }
}
