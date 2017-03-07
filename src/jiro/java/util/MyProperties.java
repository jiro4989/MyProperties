package jiro.java.util;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * <p>プロパティファイルの操作を簡単にするためのラッパークラス。</p>
 *
 * 単純にPropertiesクラスのラッパークラスであるが、以下の点で異なる。
 *
 * <ul>
 * <li>
 * {@link MyProperties#store()}メソッドは<b>XML形式</b>で出力する。<br>
 * 拡張子をpropertiesにする方法を用意していない。
 * </li> <li>
 * getProperty()は{@code null}を返すことがないように{@link java.util.Optional}で
 * ラップしている。
 * </li> <li>
 * {@link javafx.stage.Stage}や{@link javafx.scene.Node}を渡すと、Propertiesに
 * {@link javafx.stage.Stage}のx,y,width,height,maximized値を自動でセットするメ
 * ソッドを持つ。<br>
 * その際のキーはそれぞれx,y,widht,height,isMaximizedである。このキー文字列を変
 * 更することはできない。
 * </li> <li>
 * 引数に{@link javafx.stage.Stage}を渡すと{@link javafx.stage.Stage}の
 * x,y,width,height,maximizeプロパティを変更するメソッドを持つ。<br>
 * ただし渡されたプロパティファイルにそれらの値がなかった場合はセットされない。
 * </li>
 * </ul>
 * 
 * @version 1.0.0
 * @author Jiro
 *
 */
public final class MyProperties {

  private final Properties properties;
  private final File file;

  /**
   * ファイルパスのプロパティを生成する。
   *
   * @param path 生成するプロパティファイルのパス
   */
  public MyProperties(String path) {//{{{
    this(new File(path));
  }//}}}

  /**
   * プロパティファイルを生成する。
   *
   * @param aFile 生成するプロパティファイル
   */
  public MyProperties(File aFile) {//{{{
    properties = new Properties();
    file = aFile;
  }//}}}

  /**
   * プロパティファイルをロードし、成功失敗を返す。
   *
   * @return 成功の可否
   */
  public boolean load() {//{{{

    if (file.exists()) {

      try (InputStream in = new FileInputStream(file)) {

        properties.loadFromXML(in);
        return true;

      } catch (IOException e) {
        e.printStackTrace();
      }

    }

    return false;

  }//}}}

  /**
   * Stsgrの座標と幅の設定を行う。
   * <b>事前に{@link MyProperties#load()}を成功({@code true})させていないといけ
   * ない。</b>
   *
   * @param stage 座標、幅データを変更したいStageインスタンス
   */
  public void customStage(Stage stage) {//{{{

    String strX = properties.getProperty("x");
    String strY = properties.getProperty("y");
    String strW = properties.getProperty("width");
    String strH = properties.getProperty("height");

    if ( strX != null) stage.setX      ( Double.parseDouble ( strX ) ) ;
    if ( strY != null) stage.setY      ( Double.parseDouble ( strY ) ) ;
    if ( strW != null) stage.setWidth  ( Double.parseDouble ( strW ) ) ;
    if ( strH != null) stage.setHeight ( Double.parseDouble ( strH ) ) ;

    String strM = properties.getProperty("isMaximized");
    if (strM != null) stage.setMaximized(Boolean.valueOf(strM));

  }//}}}

  /**
   * XML形式でプロパティファイルを出力する。
   *
   * @param comment コメント
   * @return 成功の可否
   */
  public boolean store(String comment) {//{{{

    try (FileOutputStream out = new FileOutputStream(file)) {
      properties.storeToXML(out, comment);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return false;

  }//}}}

  /**
   * XML形式でプロパティファイルを出力する。
   *
   * @return 成功の可否
   */
  public boolean store() {//{{{
    return store(null);
  }//}}}

  /**
   * ファイルの有無を返す。
   *
   * @return 存在するかしないか
   */
  public boolean exists() {//{{{
    return file.exists();
  }//}}}

  /**
   * キーから値を取得する。<br>
   * <b>戻り値はStringではなくOptionalである。</b>
   *
   * @param key キー
   * @return 値
   */
  public Optional<String> getProperty(String key) {//{{{

    return Optional.ofNullable(properties.getProperty(key));

  }//}}}

  /**
   * Propertiesを返却する。
   *
   * @return Propertiesインスタンス
   */
  public Properties getProperties() {//{{{

    return properties;

  }//}}}

  /**
   * キーと値をセットする。<br>
   * 実質的に{@link java.util.Properties#setProperty(java.lang.String, 
   * java.lang.String)}のラッパーメソッドである。
   *
   * @param key キー
   * @param value 値
   */
  public void setProperty(String key, String value) {//{{{
    properties.setProperty(key, value);
  }//}}}

  /**
   * Stageの座標、幅、最大化状態を対象Stage内に配置されているNodeからセットする
   * 。<br>
   *
   * @param node 取得したいウィンドウに配置されているNode
   */
  public void setProperties(Node node) {//{{{
    Stage stage = (Stage) node.getScene().getWindow();
    setProperties(stage);
  }//}}}

  /**
   * Stageの座標、幅、最大化状態をセットする。<br>
   *
   * @param stage Stage
   */
  public void setProperties(Stage stage) {//{{{

    boolean isMaximized = stage.isMaximized();
    if (isMaximized)
      stage.setMaximized(false);

    properties.setProperty("x"           , "" + stage.getX());
    properties.setProperty("y"           , "" + stage.getY());
    properties.setProperty("width"       , "" + stage.getWidth());
    properties.setProperty("height"      , "" + stage.getHeight());
    properties.setProperty("isMaximized" , "" + isMaximized);

  }//}}}

}
