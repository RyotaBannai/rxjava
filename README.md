# rxjava
### Cold/ Hot producers
- `Cold`: `データのタイムライン`を Subscribe する時ごとに必ず生成する = データストリームには常に一つのサブスクライバー
- `Hot`: `データのタイムライン`は全体で一つで、消費者は同じタイムラインに参加することで、データを受け取る(別々の消費者が同時に Subscribe できる) = 過ぎてしまったデータは取得できない.

#### `Cold` -> `Hot` 変換
- `ConnectableFlowable` `ConnectableObservable`を使う.
- `refCount`: `ConnectableFlowable` `ConnectableObservable` を `Flowlable`, `Observable`に変換する. 変換する際に, 他の消費者に購読されている限りは途中から購読されても同じタイムライン情で生成されるデータを返す.(つまり, Hot な振る舞いをする)
- `autoConnect`: `ConnectableFlowable`/ `ConnectableObservable` が引数で指定した数の購読数に達した時に、自動的に subscribe メソッドを呼ぶ. 引数がない場合は、最初に subscribe メソッドが呼ばれたタイミングで処理を開始する. `autoConnect` で生成された, Flowable/ Observable は再度 subscribe を読んでも`再度処理を走ることはない`.

#### `Hot` -> `Cold` 変換
- `publish`: この処理で変換された `ConnectableFlowable`/`ConnectableObservable`は処理を開始した後に購読された場合、それ以降に生成されたデータから新たな消費者に対して通知をする.
- `replay`: publish と違って、生成したデータをキャッシュから再度生成して通知する. そのあとは同様な cold の処理。引数がない場合、全てキャッシュから生成し通知。引数がある場合、指定した個数や期間のデータをキャッシュする.
- `share`:  `ConnectableFlowable`/`ConnectableObservable` を生成せずに、hot な振る舞いをする Flowable/ Observable を作成する。実施的には, `flowable.publish().refCount()`と同様.  

### Many kinda of methods
- `FlowableProcessor` /`Subject` : Publisher と Subscriber の両方の性能を持っている interface
  - `FloableProcesso`: 他の publisher を subscribe することでデータを受け取れる consumer になることができ、さらに自分を subscribe している consumer に大してデータを通知することができる. 
  - `Subject`: Observable/ Observer の構成に使用される interface
  