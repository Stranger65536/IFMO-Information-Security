function [] = trofiv()

    z = [1  0  1  1  1  1  1   0  0  1  1   1   0   0   0   1   0  0   1   1   1   1   ];%0   0  1  0  1  1  0  0];
    %z = [0 0 0 0 0 1 1 1 1 1 1 0 1 0 1 0 1 1 0 0 1 1 0 1 1 1 0 1 1 0];
	n = length(z);
    fprintf('Decoding sequence with length %d:\n', n);
    
    for i = 1 : n
        fprintf('%d', z(i));
    end
    
    fprintf('\n\n');
    
	c = zeros(1, n);
	b = zeros(1, n);
    
	c(1) = 1;
	b(1) = 1;
    
    fprintf('%*sExecution steps:\n', 2 * n / 1.1, ' ');
    fprintf('%10s %10s %10s %*s %*s\n', 'R', 'Zi', 'delta', n + 1, 'C(x)', n + 1, 'B(x)');
    fprintf('%10d %10s %10s  ', 0, '-', '-');
    
    flag = false;
    for i = 1 : n      
        if (c(n - i + 1) == 1)
            flag = true;
        end
        
        if (flag)
            fprintf('%d', c(n - i + 1));
        else
            fprintf(' ');
        end
    end
    
    fprintf('  ');
    
    flag = false;
    for i = 1 : n
        if (b(n - i + 1) == 1)
            flag = true;
        end
        
        if (flag)
            fprintf('%d', b(n - i + 1));
        else
            fprintf(' ');
        end
    end
    
   fprintf('\n');
    %fprintf('%d', fliplr(b));

	for r = 1 : n
        
		delta = 0;
        
		for j = 1 : r
            zi = z(r - j + 1);
            cj = c(j);
            mul = zi * cj;
			delta = xor(delta, mul);
        end
        
        if (delta == 0)
            b = [0 b(1 : length(b) - 1)];
        else 
            b = [0 b(1 : length(b) - 1)];
            t = xor(c, b);
			b = c;
			c = t;
        end
        
        fprintf('%10d %10d %10d  ', r, z(r), delta);
    
        flag = false;
        for i = 1 : n      
            if (c(n - i + 1) == 1)
                flag = true;
            end
        
            if (flag)
                fprintf('%d', c(n - i + 1));
            else
                fprintf(' ');
            end
        end
    
        fprintf('  ');
    
        flag = false;
        for i = 1 : n
            if (b(n - i + 1) == 1)
                flag = true;
            end
        
            if (flag)
                fprintf('%d', b(n - i + 1));
            else
                fprintf(' ');
            end
        end
    
        fprintf('\n');
    end
end
