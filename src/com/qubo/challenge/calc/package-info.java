/**
 * <p>
 * エントリポイントクラス{@link com.qubo.challenge.calc.Main}を収めたパッケージ。
 * </p>
 * <p>
 * プログラムは引数として
 * <ul>
 * <li>数式</li>
 * <li>実数表示オプション({@code "-d"})</li>
 * <li>計算過程出力オプション({@code "-s"})</li>
 * </ul>
 * の３つを取ることができる。
 * <br />
 * 起動コマンド例：<br />
 * <code>
 * java com.qubo.caea0121.calc.Main "1 + 4 * -5"<br />
 * java com.qubo.caea0121.calc.Main 3-5/2+8 -d -s<br />
 * java com.qubo.caea0121.calc.Main 3*-5+(-7*4) -s<br />
 * </code>
 * </p>
 * <p>
 * 引数に数式を含めなかった場合、コンソールから数式の入力を求める「連続実行モード」で起動する。
 * このモードでは、引数に特にオプションが指定されていない場合、「実数表示オプションなし」「計算過程出力オプションあり」
 * の状態で起動する。
 * また、何も入力せずEnterキーを押して終了するまで、何度も計算を実行することができる。
 * </p>
 */
package com.qubo.challenge.calc;