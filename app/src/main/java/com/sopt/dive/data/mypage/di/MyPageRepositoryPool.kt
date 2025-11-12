package com.sopt.dive.data.mypage.di

import com.sopt.dive.data.mypage.datasource.MyPageDataSource
import com.sopt.dive.data.mypage.repositoryimpl.MyPageRepositoryImpl

object MyPageRepositoryPool {
    val myPageRepository by lazy {
        MyPageRepositoryImpl(
            dataSource = MyPageDataSource(ServicePool.myPageService)
        )
    }
}