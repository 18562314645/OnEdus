import request from '@/utils/request'
export default {
  // 1 讲师列表（条件查询分页）
  // current当前页 limit每页记录数 teacherQuery条件对象
  addCourse(courseInfo) {
    return request({
      url: `/eduservice/course/add`,
      method: 'post',
      data: courseInfo
    })
  },
  // 2 查询所有讲师
  getListTeacher() {
    return request({
      url: '/eduservice/teacher/findAll',
      method: 'get'
    })
  },
  // 3 查询课程详情
  getCourseInfo(courseId) {
    return request({
      url: '/eduservice/course/' + courseId,
      method: 'get'
    })
  },
  // 4 根据课程ID修改课程信息
  updataCourseInfo(courseInfo) {
    return request({
      url: '/eduservice/course/updataCourse',
      method: 'post',
      data: courseInfo
    })
  },
  // 5 获取发布课程信息
  publishCourse(courseId) {
    return request({
      url: '/eduservice/course/publishCourse/' + courseId,
      method: 'get'
    })
  },
  // 6 修改课程发布状态
  updataStatus(courseId) {
    return request({
      url: '/eduservice/course/updataStatus/' + courseId,
      method: 'post'
    })
  },
  // 7 获取课程列表
  getList(current, limit, courseQuery) {
    return request({
      url: `/eduservice/course/getCourseList/${current}/${limit}`,
      method: 'post',
      data: courseQuery
    })
  },
  // 8 根据id删除课程信息
  deleteCourse(courseId) {
    return request({
      url: '/eduservice/course/delete/' + courseId,
      method: 'delete'
    })
  }
}
