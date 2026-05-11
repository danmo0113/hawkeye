import request from '@/utils/request'

// 查询题目材料列表
export function listQuestionData(query) {
  return request({
    url: '/hawkeye/questionData/list',
    method: 'get',
    params: query
  })
}

// 查询题目材料详细
export function getQuestionData(qdId) {
  return request({
    url: '/hawkeye/questionData/' + qdId,
    method: 'get'
  })
}

// 新增题目材料
export function addQuestionData(data) {
  return request({
    url: '/hawkeye/questionData',
    method: 'post',
    data: data
  })
}

// 修改题目材料
export function updateQuestionData(data) {
  return request({
    url: '/hawkeye/questionData',
    method: 'put',
    data: data
  })
}

// 删除题目材料
export function delQuestionData(qdId) {
  return request({
    url: '/hawkeye/questionData/' + qdId,
    method: 'delete'
  })
}
