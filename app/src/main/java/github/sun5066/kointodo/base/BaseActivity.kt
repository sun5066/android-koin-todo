package github.sun5066.kointodo.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VDB: ViewDataBinding>: AppCompatActivity() {

    protected lateinit var mBinding: VDB

    @LayoutRes abstract fun getResourceId(): Int
    abstract fun initDataBinding()
    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, getResourceId())
        initDataBinding()
        initView()
    }
}