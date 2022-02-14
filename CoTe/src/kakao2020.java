public class kakao2020 {
/*
문제 설명
데이터 처리 전문가가 되고 싶은 어피치는 문자열을 압축하는 방법에 대해 공부를 하고 있습니다.
최근에 대량의 데이터 처리를 위한 간단한 비손실 압축 방법에 대해 공부를 하고 있는데, 문자열에서 같은 값이 연속해서 나타나는
것을 그 문자의 개수와 반복되는 값으로 표현하여 더 짧은 문자열로 줄여서 표현하는 알고리즘을 공부하고 있습니다.
간단한 예로 aabbaccc의 경우 2a2ba3c(문자가 반복되지 않아 한번만 나타난 경우 1은 생략함)와 같이 표현할 수 있는데,
이러한 방식은 반복되는 문자가 적은 경우 압축률이 낮다는 단점이 있습니다. 예를 들면, abcabcdede와 같은 문자열은
전혀 압축되지 않습니다. 어피치는 이러한 단점을 해결하기 위해 문자열을 1개 이상의 단위로 잘라서 압축하여 더 짧은
문자열로 표현할 수 있는지 방법을 찾아보려고 합니다.

예를 들어, ababcdcdababcdcd의 경우 문자를 1개 단위로 자르면 전혀 압축되지 않지만, 2개 단위로 잘라서 압축한다면
2ab2cd2ab2cd로 표현할 수 있습니다. 다른 방법으로 8개 단위로 잘라서 압축한다면 2ababcdcd로 표현할 수 있으며,
이때가 가장 짧게 압축하여 표현할 수 있는 방법입니다.

다른 예로, abcabcdede와 같은 경우, 문자를 2개 단위로 잘라서 압축하면 abcabc2de가 되지만, 3개 단위로 자른다면
2abcdede가 되어 3개 단위가 가장 짧은 압축 방법이 됩니다. 이때 3개 단위로 자르고 마지막에 남는 문자열은 그대로 붙여주면 됩니다.

압축할 문자열 s가 매개변수로 주어질 때, 위에 설명한 방법으로 1개 이상 단위로 문자열을 잘라 압축하여 표현한 문자열 중
가장 짧은 것의 길이를 return 하도록 solution 함수를 완성해주세요.

제한사항
s의 길이는 1 이상 1,000 이하입니다.
s는 알파벳 소문자로만 이루어져 있습니다.

 */
public int solution(String s) {
    int answer = s.length();

    //i는 비교의 기준이 되는 단위 문자열 unit의 길이를 의미한다.
    //처음엔 1글자씩 unit을 잡고 차례로 최대 길이/2 만큼 증가시킨다.
    for(int i=1; i<=s.length()/2; i++) {
        int ptr = 0;

        //tempLeng은 단위 i일때 압축되는 길이를 저장할 변수, 일단 s의 길이로 초기화
        int tempLeng = s.length();

        //문자열 s에서 ptr위치부터 ptr+1이전까지를 잘라 unit에 저정한다.
        for(;ptr+i<=s.length();) {
            String unit = s.substring(ptr, ptr+i);
            ptr += i;
            //count는 현재 단위 문자열 unit이 몇개나 반복되는지를 저장한다.
            int count = 0;

            //단위 문자열인 unit부터 다음 i개 문자가 일치하는지 확인한다.
            for(;ptr+i<=s.length();) {
                //unit과 unit다음 i개의 문자가 일치하면 count를 증가시킨다.
                if(unit.equals(s.substring(ptr,ptr+i))) {
                    count++;
                    ptr += i;
                } else {
                    //만약 unit과 다르다면 바로 unit을 다음 i개의 문자로 갱신하기 위해 반복문을 빠져나간다.
                    break;
                }
            }

            //unit을 갱신하기 전에 이번 unit으로 몇 개나 반복되었는지 확인하고, 암축된 길이만큼 tempLeng의 값을 감소시킨다.
            if(count>0) {
                tempLeng -= i*count;

                //반복된 글자수만큼 숫자가 들어가기 때문에 tempLeng은 삽입되는 숫자 갯수만큼 다시 증가한다.
                //count를 1 증가시키는 이유는 count가 9라면 실제로는 unit+unit*9 이므로 10unit과 같이 표현되기 때문이다.
                count++;
                while(count>0) {
                    count/=10;
                    tempLeng++;
                }
            }
        }

        //unit의 길이가 i일때 압축가능한 길이와 이전 answer에 저장되어 있던 이전 최소압축길이를 비교해 answer을 갱신한다.
        answer = Math.min(answer, tempLeng);
    }

    return answer;
}


/*
//최대 압축이 가능한 길이는 절반이다.
        int len = s.length()%2 == 0 ? s.length()/2 : (s.length()/2)+1;

        int min = Integer.MAX_VALUE;

        //압축 길이
        for (int i = 1; i <= len; i++) {

            StringBuilder sb = new StringBuilder("");//압축된 문자열을 저장할 객체

            int left	= 0;//왼쪽 구간
            int right 	= left+i;//오른쪽 구간
            int cnt 	= 1;//현재 중복 문자열이 몇 개인지

            String s1 = s.substring(left,left+i);//left부터 i까지의 문자열
            sb.append(s1);//압축 문자열에 추가

            while(right+i <= s.length()) {//오른쪽 구간 검사 값이 전체 문자열의 길이보다 작거나 같아야한다.

                String s2 = s.substring(right,right+i);//오른쪽 구간에 대한 문자열 생성
                if (!s1.equals(s2)) {//왼쪽과 오른쪽이 같지 않을 때,

                    left = right;//왼쪽에 오른쪽을 재구성
                    s1 = s2;

                    if (cnt > 1) sb.insert(sb.length()-i, cnt);//중복된 값이 있다면, 현재 문자열 앞에 갯수를 추가

                    sb.append(s1);//압축 문자열에 현재까지 만들어준 문자열을 저장

                    cnt = 1;//중복값을 다시 1로 만들어준다.
                } else {//왼쪽과 오른쪽이 같을 때,
                    cnt++;//중복값을 더해준다.
                }

                right += i;//오른쪽을 i만큼 증가

            }

            //마지막 오른쪽 값을 압축 문자열에 추가해주는 과정
            if (cnt > 1) sb.insert(sb.length()-i, cnt);

            sb.append(s.substring(right));
            min = Math.min(sb.length(), min);//현재 만들어준 문자열 길이와 최솟값을 비교
        }

        return min;
    }

    another
            int min = s.length();
        int len = s.length()/2+1;
        for(int i = 1; i < len; i++) {
            String before = "";
            int sum = 0;
            int cnt = 1;
            for(int j = 0; j < s.length();) {
                int start = j;
                j = (j+i > s.length()) ? s.length():j+i;
                String temp = s.substring(start, j);
                if(temp.equals(before)) {
                    cnt++;
                } else {
                    if(cnt != 1) {
                        sum += (int)Math.log10(cnt)+1;
                    }
                    cnt = 1;
                    sum+=before.length();
                    before = temp;
                }
            }
            sum+=before.length();
            if(cnt != 1) {
                sum += (int)Math.log10(cnt)+1;
            }
            min = (min > sum) ? sum : min;
        }

        return min;
    }
 */
}
