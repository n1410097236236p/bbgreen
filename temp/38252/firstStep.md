# 諸々インストール
  - 記載ミス、環境差分による問題があるかもしれないので注意
  - 以下全て、インストール実行時は ***右クリック→管理者として実行***
  
  - ## VS Code
    1. バージョンは最新で良い
    1. 「vscode インストール windows」とかで検索

  - ## Java 17
    1. 17なら基本的に大丈夫
    1. [参考](https://qiita.com/nacho4d/items/bf92bfdad80f39315584)
    1. コマンドプロンプトで `java -version` と実行してバージョンが出てきたら成功
    - 拓喜環境
      ```terminal
      hiroki@HirokinoMacBook-Pro ~ % java -version
      openjdk version "17.0.18" 2026-01-20
      OpenJDK Runtime Environment Homebrew (build 17.0.18+0)
      OpenJDK 64-Bit Server VM Homebrew (build 17.0.18+0, mixed mode, sharing)
      hiroki@HirokinoMacBook-Pro ~ %
      ```

  - ## PostgreSQL 15
    1. 「postgresql 15 インストール windows」とかで検索して、頑張ってインストールして
    1. インストールの最中に **PgAdmin** をインストールするか聞かれると思うので、それもインストール
    1. windows のサービスの一覧で  `po` とタイピングして、 **PostgreSQL 15** 的な名前のサービスがあれば成功<br>
    PgAdmin のアプリケーションがインストールされていることも要確認
    - 拓喜環境
      ```terminal
      hiroki@HirokinoMacBook-Pro ~ % psql --version
      psql (PostgreSQL) 15.15 (Homebrew)
      hiroki@HirokinoMacBook-Pro ~ % 
      ```
      
  - ## Git
    1. 「git インストール windows」とかで検索.  バージョンはなんでもいい
    1. インストール後、エクスプローラー内で *shift + 右クリック* して **Git Bash（Here）**  みたいなやつが出てきたら大丈夫

<br>
<br>

# 環境構築
  - ## リポジトリのクローン
    1. 任意のパスにワークスペースとする為のフォルダを作っておく  
    `D:\dev\[このプロジェクトのための任意の名前]` とかがおすすめ
    1. [gitHubのプロジェクト](https://github.com/n1410097236236p/bbgreen)にアクセス<br>
    もしかしたら GitHub のアカウントが必要かも<br>
    必要なら適当に作る
    1. <font color="Green"> **<>Code ↓** </font> みたいなボタンをクリック
    1. **https** が選択されていることを確認し、URLをコピー
    1. エクスプローラーでワークスペースを開いて、*shift + 右クリック* から *「Git Bash（Here）」*<br>
    ※パスがワークスペースになっていることを確認
    1. コマンド実行画面が出てくるので、`git clone [コピーしたURL]` ※ペーストするときは *shift + insert*
    1. *Enter* で実行<br>
    実行時、認証を求められる可能性がある<br>
    出てきたら作ったGitHubアカウントを入力
    1. コマンドが成功し、ワークスペースに **bbgreen** のフォルダが作成されていることを確認する
  
  - ## PgAdmin に DB インスタンスを登録
    in progress
  - ## ビルド〜起動
    in progress
