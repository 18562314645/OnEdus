import request from '@/utils/request'
export default {
  // 1 讲师列表（条件查询分页）
  // current当前页 limit每页记录数 teacherQuery条件对象
  getSubject() {
    return request({
      // url: '/eduservice/teacher/pageTeacherCondition/'+current+"/"+limit,
      url: `/eduservice/subject/getSubject`,
      method: 'get'
    })
  }
}
