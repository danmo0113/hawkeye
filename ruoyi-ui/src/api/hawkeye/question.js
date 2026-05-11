import request from '@/utils/request'

// 查询题库题目列表
export function listQuestion(query) {
  return request({
    url: '/hawkeye/question/list',
    method: 'get',
    params: query
  })
}

// 查询题库题目详细
export function getQuestion(questionId) {
  return request({
    url: '/hawkeye/question/' + questionId,
    method: 'get'
  })
}

// 新增题库题目
export function addQuestion(data) {
  return request({
    url: '/hawkeye/question',
    method: 'post',
    data: data
  })
}

// 修改题库题目
export function updateQuestion(data) {
  return request({
    url: '/hawkeye/question',
    method: 'put',
    data: data
  })
}

// 删除题库题目
export function delQuestion(questionId) {
  return request({
    url: '/hawkeye/question/' + questionId,
    method: 'delete'
  })
}
