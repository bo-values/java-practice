- classをラップする
- 配列のリサイズを知る
- メソッドとオーバーロードを知る


- printfのフォーマット指定子
以下に、Javaの`printf`メソッドで使用できるフォーマット指定子の一覧をMarkdown形式の表で示す。

| フォーマット指定子 | 対応する型                 | 説明                                                             |
|-------------------|--------------------------|----------------------------------------------------------------|
| **%b**            | `boolean`                | `true`または`false`を出力します。                                 |
| **%c**            | `char`                   | 文字を出力します。                                               |
| **%d**            | `integer`                | 10進数の整数を出力します。対応する型は`byte`、`short`、`int`、`long`です。 |
| **%e**            | `float`、`double`        | 浮動小数点数を指数表記で出力します。                             |
| **%f**            | `float`、`double`        | 浮動小数点数を10進数表記で出力します。                           |
| **%g**            | `float`、`double`        | 短い形式で浮動小数点数を出力します（%fまたは%eのうち、短い方を使用）。 |
| **%h**            | 任意の型                 | オブジェクトのハッシュコードを16進数で出力します。                 |
| **%o**            | `integer`                | 8進数で整数を出力します。対応する型は`byte`、`short`、`int`、`long`です。   |
| **%s**            | `String`または任意の型   | 文字列を出力します。`toString`メソッドを呼び出してその結果を出力します。  |
| **%t**            | `Date`または`Calendar`   | 日付と時刻の値を出力します。詳細は以下のサブフォーマット指定子で指定します。 |
| **%x**            | `integer`                | 16進数で整数を出力します。対応する型は`byte`、`short`、`int`、`long`です。  |
| **%n**            | なし                     | プラットフォームに依存する行区切り文字を出力します。                 |

### サブフォーマット指定子（%t用）

| フォーマット指定子 | 説明                   |
|-------------------|----------------------|
| **%tY**           | 4桁の年（例：2024）    |
| **%ty**           | 2桁の年（例：24）      |
| **%tm**           | 2桁の月（例：07）      |
| **%td**           | 2桁の日（例：01）      |
| **%tH**           | 2桁の時（24時間制）     |
| **%tI**           | 2桁の時（12時間制）     |
| **%tM**           | 2桁の分               |
| **%tS**           | 2桁の秒               |
| **%tL**           | 3桁のミリ秒            |
| **%tp**           | 午前または午後の表示（例：amまたはpm） |