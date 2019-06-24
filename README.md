# PwCodeEditView
### 密码，验证码
![image](https://github.com/poqiao/PwCodeEditView/blob/master/app/src/main/assets/sssssss3.gif)<br>
### 引入下面依赖</br>
```Java
implementation 'com.github.poqiao:PwCodeEditView:v1.0.1'
```
<br>## 支持两种显示格式，数字和圆点</br>
默认模式:默认个数是6个，带边框，显示圆点，圆点和边框都为黑色
```Java
<com.mxq.pq.PwCodeEditView
     android:layout_width="match_parent"
      android:layout_height="wrap_content" />
```
<br>可设置各个颜色，边线大小，圆角，可限定个数，对字体大小和圆点大小做了最大的限，不能超过可显示的范围</br>
边框模式的属性不会影响到下划线的属性

```java
public class MainActivity extends AppCompatActivity {
    private PwCodeEditView pwCodeEditView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        pwCodeEditView = (PwCodeEditView) findViewById(R.id.edit_pw);
        //设置监听器，
        pwCodeEditView.setPwCodeEditListener (new PwCodeEditView.PwCodeEditListener () {
            @Override
            public void finishInput(String pwStr) {
                //pwStr 为输入完成的文字
            }
        });

        pwCodeEditView.clear();//清空PwCodeEditView内容
        pwCodeEditView.getEditText ();//获得PwCodeEditView内容
    }
}
```
## [控件属性](https://github.com/poqiao/PwCodeEditView/blob/master/pw_code_editview/src/main/res/values/attrs.xml)

   
