package j2w.team.modules.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.ButterKnife;
import j2w.team.R;

/**
 * Created by sky on 15/2/28. dialog 基类
 */
public class J2WDialogFragment extends DialogFragment implements DialogInterface.OnShowListener {

	@Override public void onShow(DialogInterface dialog) {

	}

	/**
	 * 自定义对话框生成器
	 */
	protected static class Builder {

		/** 上下文 **/
		private final Context mContext;
		/** ViewGroup **/
		private final ViewGroup mContainer;
		/** 布局加载器 **/
		private final LayoutInflater mInflater;
		/** 标题值 **/
		private CharSequence mTitle = null;
		/** 正面-按钮值 **/
		private CharSequence mPositiveButtonText;
		/** 正面-按钮事件 **/
		private View.OnClickListener mPositiveButtonListener;
		/** 负面-按钮值 **/
		private CharSequence mNegativeButtonText;
		/** 负面-按钮事件 **/
		private View.OnClickListener mNegativeButtonListener;
		/** 中性-按钮值 **/
		private CharSequence mNeutralButtonText;
		/** 中性-按钮事件 **/
		private View.OnClickListener mNeutralButtonListener;
		/** 内容数据 **/
		private CharSequence mMessage;
		/** 主要布局 **/
		private View mCustomView;
		/** 列表适配器 **/
		private ListAdapter mListAdapter;
		/** 列表选中id **/
		private int mListCheckedItemIdx;
		/** 选择模式 **/
		private int mChoiceMode;
		/** 列表选中id集合 **/
		private int[] mListCheckedItemMultipleIds;
		/** 列表选中事件 **/
		private AdapterView.OnItemClickListener mOnItemClickListener;

		/**
		 * 构造器
		 * 
		 * @param context
		 * @param inflater
		 * @param container
		 */
		public Builder(Context context, LayoutInflater inflater, ViewGroup container) {
			this.mContext = context;
			this.mContainer = container;
			this.mInflater = inflater;
		}

		/**
		 * 获取布局加载器
		 * 
		 * @return
		 */
		public LayoutInflater getLayoutInflater() {
			return mInflater;
		}

		/**
		 * 设置标题
		 * 
		 * @param titleId
		 * @return
		 */
		public Builder setTitle(int titleId) {
			this.mTitle = mContext.getText(titleId);
			return this;
		}

		/**
		 * 设置标题
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(CharSequence title) {
			this.mTitle = title;
			return this;
		}

		/**
		 * 设置正面按钮
		 * 
		 * @param textId
		 * @param listener
		 * @return
		 */
		public Builder setPositiveButton(int textId, final View.OnClickListener listener) {
			mPositiveButtonText = mContext.getText(textId);
			mPositiveButtonListener = listener;
			return this;
		}

		/**
		 * 设置正面按钮
		 * 
		 * @param text
		 * @param listener
		 * @return
		 */
		public Builder setPositiveButton(CharSequence text, final View.OnClickListener listener) {
			mPositiveButtonText = text;
			mPositiveButtonListener = listener;
			return this;
		}

		/**
		 * 设置负面按钮
		 * 
		 * @param textId
		 * @param listener
		 * @return
		 */
		public Builder setNegativeButton(int textId, final View.OnClickListener listener) {
			mNegativeButtonText = mContext.getText(textId);
			mNegativeButtonListener = listener;
			return this;
		}

		/**
		 * 设置负面按钮
		 * 
		 * @param text
		 * @param listener
		 * @return
		 */
		public Builder setNegativeButton(CharSequence text, final View.OnClickListener listener) {
			mNegativeButtonText = text;
			mNegativeButtonListener = listener;
			return this;
		}

		/**
		 * 设置中性按钮
		 * 
		 * @param textId
		 * @param listener
		 * @return
		 */
		public Builder setNeutralButton(int textId, final View.OnClickListener listener) {
			mNeutralButtonText = mContext.getText(textId);
			mNeutralButtonListener = listener;
			return this;
		}

		/**
		 * 设置中性按钮
		 * 
		 * @param text
		 * @param listener
		 * @return
		 */
		public Builder setNeutralButton(CharSequence text, final View.OnClickListener listener) {
			mNeutralButtonText = text;
			mNeutralButtonListener = listener;
			return this;
		}

		/**
		 * 设置内容
		 * 
		 * @param messageId
		 * @return
		 */
		public Builder setMessage(int messageId) {
			mMessage = mContext.getText(messageId);
			return this;
		}

		/**
		 * 设置内容
		 * 
		 * @param message
		 * @return
		 */
		public Builder setMessage(CharSequence message) {
			mMessage = message;
			return this;
		}

		/**
		 * 设置内容集合
		 * 
		 * @param listAdapter
		 * @param checkedItemIds
		 * @param choiceMode
		 * @param listener
		 * @return
		 */
		public Builder setItems(ListAdapter listAdapter, int[] checkedItemIds, int choiceMode, final AdapterView.OnItemClickListener listener) {
			mListAdapter = listAdapter;
			mListCheckedItemMultipleIds = checkedItemIds;
			mOnItemClickListener = listener;
			mChoiceMode = choiceMode;
			mListCheckedItemIdx = -1;
			return this;
		}

		/**
		 * 设置内容集合
		 * 
		 * @param listAdapter
		 * @param checkedItemIdx
		 * @param listener
		 * @return
		 */
		public Builder setItems(ListAdapter listAdapter, int checkedItemIdx, final AdapterView.OnItemClickListener listener) {
			mListAdapter = listAdapter;
			mOnItemClickListener = listener;
			mListCheckedItemIdx = checkedItemIdx;
			mChoiceMode = AbsListView.CHOICE_MODE_NONE;
			return this;
		}

		/**
		 * 设置主要布局
		 * 
		 * @param view
		 * @return
		 */
		public Builder setView(View view) {
			mCustomView = view;
			return this;
		}

		/**
		 * 创建
		 * 
		 * @return
		 */
		public View create() {
			// 获取默认布局
			LinearLayout content = (LinearLayout) mInflater.inflate(R.layout.j2w_dialog, mContainer, false);
			/**
			 * 获取控件
			 */
			TextView vTitle = ButterKnife.findById(content, R.id.j2w_title);
			TextView vMessage = ButterKnife.findById(content, R.id.j2w_message);

			FrameLayout vCustomView = ButterKnife.findById(content, R.id.j2w_custom);
			Button vPositiveButton = ButterKnife.findById(content, R.id.j2w_button_positive);
			Button vNegativeButton = ButterKnife.findById(content, R.id.j2w_button_negative);
			Button vNeutralButton = ButterKnife.findById(content, R.id.j2w_button_neutral);
			Button vPositiveButtonStacked = ButterKnife.findById(content, R.id.j2w_button_positive_stacked);
			Button vNegativeButtonStacked = ButterKnife.findById(content, R.id.j2w_button_negative_stacked);
			Button vNeutralButtonStacked = ButterKnife.findById(content, R.id.j2w_button_neutral_stacked);
			View vButtonsDefault = ButterKnife.findById(content, R.id.j2w_buttons_default);
			View vButtonsStacked = ButterKnife.findById(content, R.id.j2w_buttons_stacked);
			ListView vList = ButterKnife.findById(content, R.id.j2w_list);

			// 设置标题样式
			vTitle.setTextAppearance(mContext, R.style.J2W_TextView_Title_Dark);
			// 设置内容样式
			vMessage.setTextAppearance(mContext, R.style.J2W_TextView_Message_Dark);
			// 设置标题值
			set(vTitle, mTitle);
			// 设置内容值
			set(vMessage, mMessage);
			// 设置填充标题和消息
			setPaddingOfTitleAndMessage(vTitle, vMessage);
			// 如果不为空 加入到布局里
			if (mCustomView != null) {
				vCustomView.addView(mCustomView);
			}
			// 列表适配器
			if (mListAdapter != null) {
				vList.setAdapter(mListAdapter);
				vList.setOnItemClickListener(mOnItemClickListener);
				if (mListCheckedItemIdx != -1) {
					vList.setSelection(mListCheckedItemIdx);
				}
				if (mListCheckedItemMultipleIds != null) {
					vList.setChoiceMode(mChoiceMode);
					for (int i : mListCheckedItemMultipleIds) {
						vList.setItemChecked(i, true);
					}
				}
			}

            /**
             * 判断字体长度 显示不同样式 
             */
			if (shouldStackButtons()) {
				set(vPositiveButtonStacked, mPositiveButtonText, mPositiveButtonListener);
				set(vNegativeButtonStacked, mNegativeButtonText, mNegativeButtonListener);
				set(vNeutralButtonStacked, mNeutralButtonText, mNeutralButtonListener);
				vButtonsDefault.setVisibility(View.GONE);
				vButtonsStacked.setVisibility(View.VISIBLE);
			} else {
				set(vPositiveButton, mPositiveButtonText, mPositiveButtonListener);
				set(vNegativeButton, mNegativeButtonText, mNegativeButtonListener);
				set(vNeutralButton, mNeutralButtonText, mNeutralButtonListener);
				vButtonsDefault.setVisibility(View.VISIBLE);
				vButtonsStacked.setVisibility(View.GONE);
			}
            //判断按钮值
			if (TextUtils.isEmpty(mPositiveButtonText) && TextUtils.isEmpty(mNegativeButtonText) && TextUtils.isEmpty(mNeutralButtonText)) {
				vButtonsDefault.setVisibility(View.GONE);
			}

			return content;
		}

		/**
		 * 设置填充标题和消息
		 */
		private void setPaddingOfTitleAndMessage(TextView vTitle, TextView vMessage) {
			int grid6 = mContext.getResources().getDimensionPixelSize(R.dimen.grid_6);
			int grid4 = mContext.getResources().getDimensionPixelSize(R.dimen.grid_4);
			if (!TextUtils.isEmpty(mTitle) && !TextUtils.isEmpty(mMessage)) {
				vTitle.setPadding(grid6, grid6, grid6, grid4);
				vMessage.setPadding(grid6, 0, grid6, grid4);
			} else if (TextUtils.isEmpty(mTitle)) {
				vMessage.setPadding(grid6, grid4, grid6, grid4);
			} else if (TextUtils.isEmpty(mMessage)) {
				vTitle.setPadding(grid6, grid6, grid6, grid4);
			}
		}

		/**
		 * 判断按钮字体长度
		 * 
		 * @return
		 */
		private boolean shouldStackButtons() {
			return shouldStackButton(mPositiveButtonText) || shouldStackButton(mNegativeButtonText) || shouldStackButton(mNeutralButtonText);
		}

		/**
		 * 判断按钮的字体长度
		 * 
		 * @param text
		 * @return
		 */
		private boolean shouldStackButton(CharSequence text) {
			final int MAX_BUTTON_CHARS = 12;
			return text != null && text.length() > MAX_BUTTON_CHARS;
		}

		private void set(Button button, CharSequence text, View.OnClickListener listener) {
			set(button, text);
			if (listener != null) {
				button.setOnClickListener(listener);
			}
		}

		private void set(TextView textView, CharSequence text) {
			if (text != null) {
				textView.setText(text);
			} else {
				textView.setVisibility(View.GONE);
			}
		}
	}
}
