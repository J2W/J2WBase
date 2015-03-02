package j2w.team.modules.dialog.iface;
/**
 * Created by sky on 15/3/2.
 */
public interface IListDialogListener {
    /**
     * 单选事件
     * @param value 值
     * @param number 选中位置
     * @param requestCode 请求编号
     */
    public void onListItemSelected(String value, int number, int requestCode);
}
