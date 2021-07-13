package github.sun5066.kointodo.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.Job

internal abstract class BaseActivity<VM: BaseViewModel, VDB: ViewDataBinding>: AppCompatActivity() {

    abstract val viewModel: VM
    lateinit var mBinding: VDB
    lateinit var mFetchJob: Job

    @LayoutRes abstract fun getResourceId(): Int
    abstract fun initDataBinding()
    abstract fun observeData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, getResourceId())
        initDataBinding()

        mFetchJob = viewModel.fetchData()
        observeData()
    }

    override fun onDestroy() {
        mFetchJob.takeIf { it.isActive }?.run { cancel() }
        super.onDestroy()
    }
}