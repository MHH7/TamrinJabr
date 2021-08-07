import java.util.Scanner;

public class Main {
    public static int maxn = 1008;
    public static String S;
    public static int change;

    static String write(int[] a){
        int mmin = -1;
        String ans = "";
        int mmax = -1;
        for(int i = maxn - 1;i >= 0;i--){
            if(a[i] != 0)mmin = i;
            if(a[i] != 0)mmax = Math.max(mmax,i);
        }
        if(mmin == -1){
            ans += '0';
            return ans;
        }
        else{
            for(int i = maxn - 1;i >= 2;i--){
                if(a[i] == 0)continue;
                if(mmax == i && a[i] < 0)ans += '-';
                if(mmax > i){
                    if(a[i] > 0)ans += '+';
                    else ans += '-';
                }
                if(Math.abs(a[i]) != 1)ans += Math.abs(a[i]);
                ans += 'x';
                ans += '^';
                ans += i;
            }
            if(a[1] != 0){
                if(mmax == 1 && a[1] < 0)ans += '-';
                if(mmax > 1){
                    if(a[1] > 0)ans += '+';
                    else ans += '-';
                }
                if(Math.abs(a[1]) != 1)ans += Math.abs(a[1]);
                ans += 'x';
            }
            if(a[0] != 0){
                if(mmax == 0 && a[1] < 0)ans += '-';
                if(mmax > 0){
                    if(a[0] > 0)ans += '+';
                    else ans += '-';
                }
                ans += Math.abs(a[0]);
            }
        }
        return ans;
    }

    static int findNum(int st){
        int neg = 1;
        if(st - 1 >= 0){
            if(S.charAt(st - 1) == '-')neg = -1;
        }
        if(S.charAt(st) == 'x')return (neg * 1);
        int t = st;
        int num = 0;
        int z = 1;
        while (S.charAt(t) >= '0' && S.charAt(t) <= '9'){
            t++;
            z *= 10;
            if(t == S.length())break;
        }
        t--;
        z /= 10;
        for(int i = st;i <= t;i++){
            num += (z * (S.charAt(i) - '0'));
            z /= 10;
        }
        if(t + 1 <= S.length() - 1){
            if(S.charAt(t + 1) == '^'){
                st = t + 2;
                t++;
                t++;
                int p = 0;
                z = 1;
                while (S.charAt(t) >= '0' && S.charAt(t) <= '9'){
                    t++;
                    z *= 10;
                    if(t == S.length())break;;
                }
                t--;
                z /= 10;
                for(int i = st;i <= t;i++){
                    p += (z * (S.charAt(i) - '0'));
                    z /= 10;
                }
                num = (int)Math.pow(num,p);
            }
        }
        return (neg * num);
    }

    static int last(int st){
        int cnt = 0;
        while (1 == 1){
            if(S.charAt(st) == ')')cnt--;
            if(S.charAt(st) == '(')cnt++;
            if(cnt == 0)break;
            st++;
        }
        return st;
    }

    static int first(int en){
        int cnt = 0;
        while (1 == 1){
            if(S.charAt(en) == '(')cnt--;
            if(S.charAt(en) == ')')cnt++;
            if(cnt == 0)break;
            en--;
        }
        return en;
    }

    static int[] solve(int st, int en) {
        change = 0;
        int change2 = 0;
        int[] ans = new int[maxn];
        int[] a = new int[maxn];
        int[] b = new int[maxn];
        for(int i = st;i <= en;i++){
            if(S.charAt(i) == '#'){
                int[] temp = new int[maxn];
                a = solve(first(i - 1),i - 1);
                en += change;
                i += change;
                b = solve(i + 1,last(i + 1));
                en += change;
                for(int j = 0;j < maxn;j++){
                    temp[j] += (a[j] * b[j]);
                }
                int ff = first(i - 1);
                int ss = last(i + 1);
                int l = ss - ff + 1;
                String s = write(temp);
                StringBuilder sb = new StringBuilder(s);
                sb.insert(0,'(');
                sb.insert(s.length() + 1,')');
                s = sb.toString();
                en -= l;
                en += s.length();
                change2 += (s.length() - l);
                i = ff - 1;
                StringBuffer t = new StringBuffer(S);
                t.replace(ff,ss + 1,s);
                S = t.toString();
            }
        }
        for(int i = st;i <= en;i++){
            if(S.charAt(i) == '(' && i != st){
                if(S.charAt(i - 1) == ')'){
                    int[] temp = new int[maxn];
                    a = solve(first(i - 1),i - 1);
                    en += change;
                    i += change;
                    b = solve(i,last(i));
                    en += change;
                    for(int j = 0;j < maxn;j++){
                        for(int k = 0;k < maxn;k++){
                            if(k + j >= maxn)break;
                            temp[k + j] += (a[j] * b[k]);
                        }
                    }
                    int ff = first(i - 1);
                    int ss = last(i);
                    int l = ss - ff + 1;
                    String s = write(temp);
                    StringBuilder sb = new StringBuilder(s);
                    sb.insert(0,'(');
                    sb.insert(s.length() + 1,')');
                    s = sb.toString();
                    en -= l;
                    en += s.length();
                    change2 += (s.length() - l);
                    i = ff - 1;
                    StringBuffer t = new StringBuffer(S);
                    t.replace(ff,ss + 1,s);
                    S = t.toString();
                }
            }
        }
        int neg = 1;
        int op = 0;
        for(int i = st;i <= en;i++){
            if(S.charAt(i) == '+' || S.charAt(i) == '-'){
                if(S.charAt(i) == '-'){
                    if(i + 1 <= en){
                        if(S.charAt(i + 1) == '(')neg = -1;
                    }
                }
            }
            else if(S.charAt(i) == '('){
                op = 1;
            }
            else if(S.charAt(i) == ')'){
                op = 0;
                neg = 1;
            }
            else{
                int x = findNum(i);
                while (S.charAt(i) != 'x' && S.charAt(i) != '+' && S.charAt(i) != '-' && S.charAt(i) != '(' && S.charAt(i) != ')'){
                    if(i == en)break;
                    i++;
                }
                if(S.charAt(i) == '+' || S.charAt(i) == '-' || S.charAt(i) == '(' || S.charAt(i) == ')')i--;
                if(S.charAt(i) != 'x'){
                    ans[0] += (neg * x);
                }
                else if(i == en){
                    ans[1] += (neg * x);
                }
                else if(S.charAt(i + 1) != '^'){
                    ans[1] += (neg * x);
                }
                else{
                    i++;
                    i++;
                    if(S.charAt(i) == '('){
                        int[] temp = new int[maxn];
                        temp = solve(i,last(i));
                        ans[temp[0]] += (neg * x);
                        i = last(i);
                    }
                    else{
                        ans[findNum(i)] += (neg * x);
                        while (S.charAt(i) >= '0' && S.charAt(i) <= '9'){
                            if(i == en)break;
                            i++;
                        }
                        if(S.charAt(i) < '0' || S.charAt(i) > '9')i--;
                    }
                }
            }
        }
        change = change2;
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        S = sc.nextLine();
        int[] ans = new int[maxn];
        ans = solve(0,S.length() - 1);
        String sans = write(ans);
        System.out.println(sans);
    }
}
