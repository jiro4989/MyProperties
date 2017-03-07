MyFileChooser
================================================================================

JavaFX用に独自にラッピングしたFileChooserクラス。

- Version      : 1.0.0
- Java Version : 1.8.0_121
- Author       : 次郎 (Jiro)
- Since        : Mar 06, 2017
- Last Changed : Mar 06, 2017

ライブラリ概要
--------------------------------------------------------------------------------

FileChooserをラッピングし、メソッドのnull安全性を保証したクラス。

openFile()といった各種メソッドの戻り値はすべてOptionalでラッピングされる。

ダイアログを開いたときの初期ディレクトリはデフォルトでカレントディレクトリになっ
ている。よってinitDir(".")といった呼び出しは不要である。

ダイアログを表示してファイル選択に成功した時、初期ディレクトリや初期ファイル名を
自動で変更するオプションを持つ。このオプションはデフォルトでtrueになっている。

Properteisインスタンスを渡すことで、開いたファイルの初期ディレクトリパスやファイ
ル名をプロパティに自動でセットするオプションを持つ。

使い方
--------------------------------------------------------------------------------

インスタンス生成時に必須プロパティとして、ExtensionFilterの説明文と拡張子文字列
を渡します。これらのプロパティは`null`を受け付けません。それらが渡されると例外を
返します。

Builderコンストラクタを呼び出したあと、`Builder#build()`を呼び出すことで
MyFileChooserのインスタンスが生成されます。

この時、`Builder#build()`する前に各種プロパティのセッターメソッドでチェーンする
ことで、各種オプションを変更できます。

```java

import jiro.javafx.stage.MyFileChooser;

MyFileChooser mfc = MyFileChooser.Builder("Text Files", "*.txt")
  .initFileName("init_file")
  .properties(properties)
  .initDirKey("initDir")
  .initFileNameKey("initFileName")
  .build();

mfc.openFile().ifPresent(file -> {
  doSomething(file);
});

```

更新履歴
--------------------------------------------------------------------------------

Ver1.0.0 : 2017/03/06
- ライブラリ公開
