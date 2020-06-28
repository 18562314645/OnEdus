import request from '@/utils/request'
export default {
  // 1 讲师列表（条件查询分页）
  // current当前页 limit每页记录数 teacherQuery条件对象
  getAllChapterVideo(courseId) {
    return request({
      url: `/eduservice/chapter/getChapter/${courseId}`,
      method: 'get'
    })
  },
  addChapter(chapter) {
    return request({
      url: '/eduservice/chapter/addChapter',
      method: 'post',
      data: chapter
    })
  },
  updateChapter(chapter) {
    return request({
      url: '/eduservice/chapter/updataChapter',
      method: 'post',
      data: chapter
    })
  },
  deleteChapter(chapterId) {
    return request({
      url: '/eduservice/chapter/' + chapterId,
      method: 'delete'
    })
  },
  getChapter(chapterId) {
    return request({
      url: '/eduservice/chapter/getChapterInfo/' + chapterId,
      method: 'get'
    })
  }
}
