`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2021/11/24 15:09:01
// Design Name: 
// Module Name: parser
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


module parser#(parameter size=8)(data,clk,set,out);
    input [size-1:0] data;        //载入的数据
    input clk;
    input set;
    output reg out;
                  //串行输出最低位
    reg [size-1:0] load;          //临时寄存器存储载入的值
    always@(posedge clk)
    begin
        if (set)
            load <= data;
        else
            load <= {1'b0,load[7:1]};
    end

    always@(*)
    begin
        out <= load[0];
    end
endmodule


