`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2021/11/15 09:29:46
// Design Name: 
// Module Name: devide
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module devide #(parameter size=8)(
input [size-1:0]a,
input [size-1:0]b,
output [size-1:0]consult,
output [size-1:0]remainder
    );
    reg [2*size-1:0] a_16;
    reg [2*size-1:0] b_16;
    always@(*)begin
        if(b==0)begin
            a_16={16'b1111_1111_1111_1111};
        end
        else begin
            a_16={8'b0000_0000,a};
            b_16={b,8'b0000_0000};
            
            repeat(size)begin
                a_16=a_16<<1;
                if(a_16>=b_16)begin
                    a_16=a_16-b_16+1'b1;
                end
                else begin
                    a_16=a_16;
                end
            end
        end
    end
    assign consult=a_16[size-1:0];
    assign remainder=a_16[size*2-1:size];
endmodule
