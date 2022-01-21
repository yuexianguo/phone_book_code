package com.phone.book.common.data.source




/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2020/4/23
 */
class StrutDataRepository : StrutDataSource {

//    override fun getFixtureList(
//        action: Int,
//        success: Consumer<in FixtureAddressesResponse?>,
//        error: Consumer<in Throwable?>
//    ): Disposable {
//        val params = JsonObject()
//        params.addProperty("action", action)
//        LogUtil.d(BaseDataSource.TAG, "getDeviceList: request json=$params")
//        return ObservableRepository.getFixtureObservable(params)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .map { jsonObject ->
//                LogUtil.d(BaseDataSource.TAG, "getDeviceList: response json=$jsonObject")
//                val fixtureListResponse =
//                    Gson().fromJson(jsonObject.toString(), FixtureAddressesResponse::class.java)
//                if (ResponseResult.RESULT_SUCCESS == fixtureListResponse.result) {
//                    DbUtils.compareFixturesWithSaved(fixtureListResponse)
//                    DbUtils.saveSCMData(fixtureListResponse)
//                } else {
//                    throw Throwable("Response error, code=${fixtureListResponse.result}")
//                }
//                fixtureListResponse
//            }
//            .subscribe(success, error)
//    }


}