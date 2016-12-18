function trofiv()
    clc;
    
    buildAndCheckField();
    
    %p = generatePrimeNumber(100, 256); 
    p = 211;
    %q = generatePrimeNumber(100, 256); 
    q = 197;
    n = p * q;
    %n = 41567;
    fi = (p - 1) * (q - 1);
    %fi = 41160;
    %[e, d] = generateKeys(p, q); 
    e = 6707;
    d = 38963;
    
    fprintf('p:  %10d\nq:  %10d\nn:  %10d\nfi: %10d\ne:  %10d\nd:  %10d\n\n', p, q, n, fi, e, d);
    
    encode('input.txt', 'encode.txt', e, n);    
    fprintf('encoding done\n');
    encode('encode.txt','decode.txt', d, n);     
    fprintf('decoding done\n');
end

function [result] = leftShift(a)
    result = circshift(a, [0 -1]);
    result(length(result)) = 0;
end

function [result] = rightShift(a)
    result = circshift(a, [0 1]);
    result(1) = 0;
end

function encode(inputFileName, outputFileName, key, n)

    input = fopen(inputFileName, 'rb');
    inputBuffer = fread(input, 'uint16');
    fclose(input);
    inputBuffer = inputBuffer';
    outputBuffer = zeros(length(inputBuffer), 1);
    
    for i = 1 : length(inputBuffer)
        x = inputBuffer(i);
        y = powMod(x, key, n);
        outputBuffer(i) = y;
    end
    
    output = fopen(outputFileName, 'wb');
    outputBuffer = outputBuffer';
    fwrite(output, outputBuffer, 'uint16');
    fclose(output);
    
end

function [result] = powMod(x, n, p)

    binN = dec2bin(n);
    binN = binN - '0';

    result = x;
    
    for i = 2 : length(binN)
       result = mod(result * result, p);
       if (binN(i) == 1)
           result = mod(result * x, p);
       end
    end

end

function [e, d] = generateKeys(p, q)

    fi = (p - 1) * (q - 1); 
    e = random(2, fi);
    
    while (true)
        [result, ~, y] = gcd(fi, e);
        if (result == 1)
            d = mod(y, fi);
            break;
        else
            e = random(2, fi);
        end
    end
end

function[result] = generatePrimeNumber(lowerBound, upperBound)
    p = random(lowerBound, upperBound);
    while ~(isProbablyPrime(p, 100) && isprime(p))
        p = p + 1;
    end
    result = p;
end

function [result, x, y] = gcd(a0, a1)             
    x1 = 0;
    y1 = 1;
    
    x2 = 1;
    y2 = 0;
    
    while (a1 > 0) 
        q = floor(a0 / a1);
        ai = a0 - q * a1;
        x = x2 - q * x1;
        y = y2 - q * y1;
        
        a0 = a1;
        a1 = ai;
        
        x2 = x1;
        x1 = x;
        
        y2 = y1;
        y1 = y;
    end
    
    result = a0;
    x = x2;
    y = y2;
end   

function buildAndCheckField()

    fprintf('p(x) = x^3 + x^2 + 1\n\n');

    a = zeros(2 ^ 3, 3);                       
    a(1, :) = [0 0 0];
    a(2, :) = [0 0 1];
    
    for i = 3 : 2 ^ 3    
        
        a(i, 1) = a(i - 1, 2);
        a(i, 2) = a(i - 1, 3);
        a(i, 3) = 0;  
        
        if (a(i - 1, 1) == 1)             
            a(i, 3) = xor(a(i, 3), 1);
            a(i, 1) = xor(a(i, 1), 1);
        end
        
    end
      
    for i = 1 : 2 ^ 3        
        
        fprintf('a%d = ', i - 1); 
        
        count = sum(a(i, :) == 1);
        firstIndex = find(a(i, :) == 1);

        if (count == 0)
            fprintf('0');
        end
        
        for j = 1 : 3
            
            if (a(i, j) == 1)
                
                if (count ~= 1 && j ~= firstIndex(1))
                    fprintf('+ ');
                end
                
                if (j < 2)
                    fprintf('x^%d ', 3 - j);
                end
                
                if (j == 2)
                    fprintf('x ');
                end
                
                if (j == 3)
                    fprintf('1');
                end
                
            end
        end
        
        fprintf('\n');
    end
    
    plus = zeros(2 ^ 3, 2 ^ 3);
    
    fprintf('\n+  ');
    for i = 1 : 2 ^ 3
        fprintf('%d ', bin2dec(num2str(a(i, :))));
    end
    fprintf('\n');
    for i = 0 : 2 ^ 4 + 1
        fprintf('-');
    end
    fprintf('\n');
    
    for i = 1 : 2 ^ 3    
        fprintf('%d| ', bin2dec(num2str(a(i, :))));
        for j = 1 : 2 ^ 3
            plus(i, j) = bin2dec(num2str(xor(a(i, :), a(j, :))));
            fprintf('%d ', plus(i, j));
        end
        fprintf('\n');
    end
    
    fprintf('\n');
    
    mul = zeros(2 ^ 3, 2 ^ 3);
    
    fprintf('\n*  ');
    for i = 1 : 2 ^ 3
        fprintf('%d ', bin2dec(num2str(a(i, :))));
    end
    fprintf('\n');
    for i = 0 : 2 ^ 4 + 1
        fprintf('-');
    end
    fprintf('\n');
    
    for i = 1 : 2 ^ 3    
        fprintf('%d| ', bin2dec(num2str(a(i, :))));
        for j = 1 : 2 ^ 3
            
            iBin = a(i, :);
            jBin = a(j, :);
            
            mul(i, j) = multiply(iBin, jBin);
            
            fprintf('%d ', mul(i, j));
        end
        fprintf('\n');
    end
    
    fprintf('\n');
    
end

function [result] = multiply(a, b)

    p = zeros(length(a), 1);
    p = p';
    overflow = zeros(length(a), 1); 
    overflow = overflow'; %#ok<NASGU>
    
    for i = 1 : length(a)
        
        decB = bin2dec(num2str(and(b, [0 0 1])));
        
        if (decB == 1)
            p = xor(p, a);
        end
        
        overflow = bin2dec(num2str(and(a, [1 0 0])));
        a = leftShift(a);
        
        if (overflow ~= 0)
            a = xor(a, [1 0 1]);
        end
        
        b = rightShift(b);
        
    end
    
    result = bin2dec(num2str(p));
    
end

function [result] = isProbablyPrime(p, s)              

    for j = 1 : s
        
        a = random(1, p - 1);
       
        if witness(a, p)
            result = false;
            return;
        end
        
    end
    
    result = true;
end

function [result] = random(a, b) 
    result = round(rand * (b - a)) + a;
end

function [result] = witness(a, p)                   

    result = false;
    t = num2str(dec2bin(p - 1)); 
    
    b = zeros(length(t), 1);
    
    for i = 1 : length(t)
        b(i) = str2double(t(i)); 
    end
    
    b = b(end : -1 : 1);
    d = 1;
    
    for  i = length(b) : -1 : 1
        
        x = d;
        d = mod((d * d), p);
        
        if (d == 1) && (x ~= 1) && (x ~= p-1)
            result = true;
            return;
        end
        
        if (b(i))
            d = mod((d * a), p);
        end
        
    end
    if (d ~= 1)
        result = true;
        return; 
    end
end   
